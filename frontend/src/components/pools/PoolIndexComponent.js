import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class PoolIndexComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,

      pools: [],
    };

    this.delete = this.delete.bind(this);
  }

  componentDidMount() {
    this.getPools();
  }

  getPools() {
    axios
      .get(process.env.REACT_APP_API_URL + '/pools')
      .then(result => {
        this.setState({
          isLoading: false,
          pools: result.data,
        });
      })
      .catch(error => {
        this.setState({
          isLoading: false,
          pools: [],
        });
        this.props.enqueueSnackbar('Error al cargar los pozos!', {
          variant: 'error',
        });
      });
  }

  delete(event) {
    if (window.confirm('¿Esta seguro que desea eliminar el pozo?')) {
      this.setState({ isLoading: true });
      const id = event.target.id.value;

      axios
        .delete(process.env.REACT_APP_API_URL + '/pools/' + id)
        .then(result => {
          this.props.enqueueSnackbar('Pozo eliminado!', { variant: 'success' });

          this.getPools();
        })
        .catch(error => {
          this.setState({ isLoading: false });
          this.props.enqueueSnackbar('Error al eliminar el pozo!', {
            variant: 'error',
          });
        });
    }

    event.preventDefault();
  }

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <LinearProgress className="progress" />
        ) : (
          <div>
            <div className="btn-group">
              <Button
                variant="contained"
                color="primary"
                className="btn-primary"
                component={Link}
                to="/pools/create"
              >
                Crear Nuevo Pozo
              </Button>
            </div>
            <Typography variant="h4" gutterBottom>
              Pozos de preguntas 2018
            </Typography>

            <Paper>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Nombre del Pozo</TableCell>
                    <TableCell />
                    <TableCell />
                    <TableCell />
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.pools.map(pool => {
                    return (
                      <TableRow key={pool.id}>
                        <TableCell component="th" scope="pool">
                          {pool.name}
                        </TableCell>
                        <TableCell>
                          <Button
                            color="default"
                            component={Link}
                            to={'/pools/' + pool.id}
                          >
                            Ver
                          </Button>
                        </TableCell>
                        <TableCell>
                          <Button
                            color="primary"
                            component={Link}
                            to={'/pools/' + pool.id + '/edit'}
                          >
                            Editar
                          </Button>
                        </TableCell>
                        <TableCell>
                          <form onSubmit={this.delete}>
                            <input type="hidden" name="id" value={pool.id} />
                            <Button color="secondary" type="submit">
                              Borrar
                            </Button>
                          </form>
                        </TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </Paper>
          </div>
        )}
      </div>
    );
  }
}

export default withSnackbar(PoolIndexComponent);
