import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';

import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import FilledInput from '@material-ui/core/FilledInput';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Grid from '@material-ui/core/Grid';
import Tooltip from '@material-ui/core/Tooltip';

const variables = `
Ingrese las variables presentes en su código, además del rango que puede tomar cada una de ellas.
Ejemplo: a=(1,5) donde a, es la variable y (1,5) es el rango de valores que puede tomar.
`;
const enunciado = `
Ingrese el enunciado que tendrá su código.
`;
const codigo = `
Ingrese el código Python que tendrá su pregunta.
`;
const pozo = `
Seleccione el pozo al que pertenecerá su pregunta.
`;

class QuestionEditComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,

      question: {
        id: this.props.match.params.id,
        poolId: '',
        title: '',
        code: '',
        variables: {},
      },

      pools: [],

      errors: {
        title: '',
        variables: '',
        code: '',
      },
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.getPools();
  }

  getPools() {
    axios
      .get(process.env.REACT_APP_API_URL + '/pools')
      .then(result => {
        this.setState({
          pools: result.data,
        });
        this.getQuestion();
      })
      .catch(error => {
        this.setState({
          pools: [],
          isLoading: false,
        });
        this.props.enqueueSnackbar('Error al cargar los datos!', {
          variant: 'error',
        });
      });
  }

  getQuestion() {
    axios
      .get(
        process.env.REACT_APP_API_URL + '/questions/' + this.state.question.id
      )
      .then(result =>
        this.setState({
          isLoading: false,
          question: result.data,
        })
      )
      .catch(error => {
        this.setState({
          isLoading: false,
          question: null,
        });
        this.props.enqueueSnackbar('Error al cargar los datos!', {
          variant: 'error',
        });
      });
  }

  handleChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      question: {
        ...this.state.question,
        [name]: value,
      },
    });
  }

  validateSubmit(event) {
    // const form = event.target;
    const question = this.state.question;
    var outcome = true;
    // const properties = [
    //   'title',
    //   'code',
    //   'variables'
    // ];

    try {
      if (question.title.length === 0 || question.title.length > 256) {
        this.setState({
          errors: {
            name:
              'El campo enunciado es requerido y su largo debe ser menor a 256 caracteres.',
          },
        });
        outcome = false;
      }

      if (question.code.length === 0) {
        this.setState({ errors: { name: 'El campo código es requerido.' } });
        outcome = false;
      }
    } catch (err) {
      this.props.enqueueSnackbar('Error al guardar pregunta!', {
        variant: 'error',
      });
    }

    if (!outcome) {
      this.props.enqueueSnackbar(
        'Debes completar todos los campos correctamente!',
        { variant: 'warning' }
      );
    }

    event.preventDefault();
    event.stopPropagation();
    return outcome;
  }

  handleSubmit(event) {
    if (!this.validateSubmit(event)) {
      event.preventDefault();
      return;
    }

    this.setState({ isLoading: true });

    axios
      .post(process.env.REACT_APP_API_URL + '/questions', this.state.question)
      .then(result => {
        this.setState({
          isLoading: false,
          question: result.data,
        });

        this.props.history.push('/questions');
        this.props.enqueueSnackbar('Pregunta creada!', { variant: 'success' });
      })
      .catch(error => {
        this.setState({ isLoading: false });
        this.props.enqueueSnackbar('Error al crear la pregunta!', {
          variant: 'error',
        });
      });

    event.preventDefault();
  }

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <LinearProgress className="progress" />
        ) : (
          <div>
            <Typography variant="h4" gutterBottom>
              Editar Pregunta
            </Typography>

            <Grid container spacing={24}>
              <Grid item xs={12}>
                <div class="panelcrear">
                  <div class="formcrear">
                    <form onSubmit={this.handleSubmit}>
                      <Tooltip title={pozo} placement="right">
                        <FormControl variant="filled" margin="normal" fullWidth>
                          <InputLabel htmlFor="poolId">Pozo</InputLabel>
                          <Select
                            value={this.state.question.poolId}
                            onChange={this.handleChange}
                            input={<FilledInput name="poolId" id="poolId" />}
                          >
                            {this.state.pools.map(pool => {
                              return (
                                <MenuItem key={pool.id} value={pool.id}>
                                  {pool.name}
                                </MenuItem>
                              );
                            })}
                            ;
                          </Select>
                        </FormControl>
                      </Tooltip>

                      <Tooltip title={enunciado} placement="right">
                        <TextField
                          id="title"
                          name="title"
                          label="Enunciado"
                          value={this.state.question.title}
                          placeholder="Enunciado de la pregunta"
                          multiline
                          rows="5"
                          onChange={this.handleChange}
                          fullWidth
                          margin="normal"
                          variant="filled"
                          InputLabelProps={{
                            shrink: true,
                          }}
                        />
                      </Tooltip>
                      <Tooltip title={variables} placement="right">
                        <TextField
                          id="variables"
                          name="variables"
                          label="Variables"
                          value={this.state.question.variables}
                          placeholder="Ingrese variables del código"
                          multiline
                          rows="3"
                          onChange={this.handleChange}
                          fullWidth
                          margin="normal"
                          variant="filled"
                          InputLabelProps={{
                            shrink: true,
                          }}
                        />
                      </Tooltip>

                      <Tooltip title={codigo} placement="right">
                        <TextField
                          id="code"
                          name="code"
                          label="Código"
                          value={this.state.question.code}
                          placeholder="Ingrese código Python"
                          multiline
                          rows="10"
                          onChange={this.handleChange}
                          fullWidth
                          margin="normal"
                          variant="filled"
                          InputLabelProps={{
                            shrink: true,
                          }}
                        />
                      </Tooltip>
                      <div className="btn-group">
                        <Button component={Link} to="/questions">
                          Cancelar
                        </Button>
                        <Button
                          variant="contained"
                          color="primary"
                          className="btn-primary"
                          type="submit"
                        >
                          Guardar
                        </Button>
                      </div>
                    </form>
                  </div>
                </div>
              </Grid>
              {/*<Grid item xs={6}>
              <Typography variant="h6" gutterBottom>Vista Previa</Typography>
            </Grid>*/}
            </Grid>
          </div>
        )}
      </div>
    );
  }
}

export default withSnackbar(QuestionEditComponent);
